# Stage 1: Build Stage, Set up the build environment, use node image, node.js runtime environment, npm package manager, build the app, generate static files
FROM node:20-alpine AS build

WORKDIR /app

# Copy the package.json in the current working directory app, copy both 'package.json' and 'package-lock.json' (if available)
#COPY package*.json .
COPY package*.json /app/

# Install the dependencies
RUN npm install

# Copy the all the files in the current working directory app
COPY . .

# Build the app
RUN npm run build

# Stage 2: Set up the server, use nginx image, web open source server and high cuncurrency, generate static files, serve the static files to the client, reverse proxy, load balancer, cache, etc. Port 80
FROM nginx:alpine-slim

# Copy the build files from the build stage to the nginx server
COPY nginx.conf /etc/nginx/nginx.conf

# Stage 3: Copy the build resources from the build stage to the nginx server
COPY --from=build /app/dist/book-network-front/browser/ /usr/share/nginx/html

EXPOSE 80