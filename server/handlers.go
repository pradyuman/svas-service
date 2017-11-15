package server

import (
	"encoding/json"
	"log"

	"github.com/gin-gonic/gin"
)

type Handlers struct {
	Status Status
}

func (h *Handlers) GetStatus(ctx *gin.Context) {
	Success(ctx, h.Status)
}

func (h *Handlers) PostRequest(ctx *gin.Context) {
	if err := json.NewDecoder(ctx.Request.Body).Decode(&h.Status); err != nil {
		log.Fatal("Update Error:", err.Error())
	}
	Success(ctx, h.Status)
}
