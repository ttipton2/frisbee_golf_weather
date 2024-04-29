package main

import (
	"context"
	"log"
	"net/http"
	"os"

	"cs455_server/routes"

	"github.com/nahojer/httprouter"
	"github.com/nahojer/httprouter/middleware"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

func healthCheck(w http.ResponseWriter, req *http.Request) error {
	w.Write([]byte("healthy"))
	return nil
}

func main() {
	logger := log.New(os.Stdout, "INFO: ", log.Ldate|log.Ltime)
	logger.Println("This is an info message")
	uri := os.Getenv("MONGO_URL")
	logger.Printf("MONGO_URL = %s", uri)
	serverAPI := options.ServerAPI(options.ServerAPIVersion1)
	opts := options.Client().ApplyURI(uri).SetServerAPIOptions(serverAPI)
	opts.SetDirect(true)

	client, err := mongo.Connect(context.TODO(), opts)
	if err != nil {
		logger.Println("Error when Connecting to MongoDB")
		panic(err)
	}
	defer func() {
		if err = client.Disconnect(context.TODO()); err != nil {
			logger.Fatal("Error when Disconnecting")
			panic(err)
		}
	}()
	var result bson.M

	if err := client.Database("admin").RunCommand(context.TODO(), bson.D{{"ping", 1}}).Decode(&result); err != nil {
		panic(err)
	}
	logger.Println("Connected to MongoDB!")

	coll := client.Database("diskit-db").Collection("courses")

	courses := routes.CourseModel(coll, logger)

	r := httprouter.New() // new router
	r.Use(middleware.RecoverPanics())
	r.Handle(http.MethodGet, "/health", healthCheck)
	r.Handle(http.MethodGet, "/region/:city", courses.GetCoursesByRegion)
	r.Handle(http.MethodGet, "/status/:id", courses.GetCourseStats)
	r.Handle(http.MethodGet, "/course/:id", courses.GetCourse)
	r.Handle(http.MethodGet, "/status/population", courses.GetPopulation)
	r.Handle(http.MethodPost, "/crowded/:id", courses.IncrementCrowd)
	r.Handle(http.MethodPost, "/empty/:id", courses.IncrementEmpty)
	r.Handle(http.MethodPost, "/rain/:id", courses.IncrementRain)
	r.Handle(http.MethodPost, "/rain/:wind", courses.IncrementWindy)
	r.Handle(http.MethodPost, "/courses", courses.PostCourse)
	go log.Fatal(http.ListenAndServe(":3000", r))
}
