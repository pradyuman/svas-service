package server

import (
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/gin-gonic/gin"
)

func Router(h *Handlers) {
	server := gin.Default()

	server.GET("/ping", func(ctx *gin.Context) {
		ctx.Status(http.StatusOK)
	})

	server.GET("/status", h.GetStatus)
	//server.POST("/status", h.PostStatus)
	//server.GET("/request", h.GetRequest)
	server.POST("/request", h.PostRequest)

	port := os.Getenv("PORT")
	log.Printf("Server listening on port %s", port)
	server.Run(fmt.Sprintf(":%s", port))
}
