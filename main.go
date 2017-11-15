package main

import (
	"github.com/pradyuman/svas-service/server"
)

func main() {
	h := &server.Handlers{}
	server.Router(h)
}
