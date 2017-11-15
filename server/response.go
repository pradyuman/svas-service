package server

import (
	"log"
	"net/http"

	"github.com/gin-gonic/gin"
)

func Success(ctx *gin.Context, json interface{}) {
	ctx.Header("Content-Type", "application/json")
	ctx.Header("Access-Control-Allow-Origin", "*")
	ctx.JSON(http.StatusOK, json)
}

func Failure(ctx *gin.Context, e error, status int) {
	log.Printf("[ERROR] %s %s: %v", ctx.Request.Method, ctx.Request.URL.Path, e)
	ctx.Header("Access-Control-Allow-Origin", "*")
	ctx.JSON(status, gin.H{
		"status":  status,
		"message": e.Error(),
		"method":  ctx.Request.Method,
		"path":    ctx.Request.URL.Path,
		"query":   ctx.Request.URL.Query(),
	})
}
