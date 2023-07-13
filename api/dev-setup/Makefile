# Detect the OS
ifeq ($(OS),Windows_NT)
    detected_OS := Windows
else
    detected_OS := $(shell uname -s)
endif

# Show makefile utilities for the project (default command)
default: help

# Build project with Maven and Docker, and then start containers
build-up:
ifeq ($(detected_OS),Windows)
	cd .. && mvnw clean install && copy .\target\api-0.0.1-SNAPSHOT.jar .
else
	cd .. && ./mvnw clean install && cp ./target/api-0.0.1-SNAPSHOT.jar .
endif
	docker compose build
	docker compose up -d

# Build project with Maven (skipping tests) and Docker, and then start containers
build-up-skip-tests:
ifeq ($(detected_OS),Windows)
	cd .. && mvnw clean install -DskipTests && copy .\target\api-0.0.1-SNAPSHOT.jar .
else
	cd .. && ./mvnw clean install -DskipTests && cp ./target/api-0.0.1-SNAPSHOT.jar .
endif
	docker compose build
	docker compose up -d

# Start containers
up:
	docker compose up -d

# Stop containers and remove containers, networks, volumes, and images created by up
down:
	docker compose down

# Stop running containers without removing them
stop:
	docker compose stop

# Start existing containers
start:
	docker compose start

# Stop running containers without removing them and start them in detached mode
restart:
	docker compose stop && docker compose up -d

# Remove stopped service containers
rm:
	docker compose rm

# Follow logs output
logs:
	docker compose logs -f

# Show help
help:
	@echo "Usage: make [TARGET]"
	@echo "TARGETS:"
	@echo "  build-up             Build project with Maven and Docker, and then start containers"
	@echo "  build-up-skip-tests  Build project with Maven (skipping tests) and Docker, and then start containers"
	@echo "  up                   Start containers in detached mode"
	@echo "  down                 Stop containers and remove containers, networks, volumes, and images created by up"
	@echo "  stop                 Stop running containers without removing them"
	@echo "  start                Start existing containers"
	@echo "  restart              Stop running containers without removing them and start them in detached mode"
	@echo "  rm                   Remove stopped containers"
	@echo "  logs                 Follow logs output"
	@echo "  help                 Show this help"

.PHONY: default build-up build-skip-tests-up up up-d down stop start restart rm logs help
