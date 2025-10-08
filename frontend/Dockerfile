# STAGE 1: BUILD WITH NODE

# get node image
FROM node:20-alpine AS build

# create /app directory
WORKDIR /app

# copy package.json and package-lock.json into /app directory
# this lets docker cache the dependency install
COPY package*.json ./

# docker equivalent of npm install
# installs node modules exactly as in package-lock.json
RUN npm ci

# copies the rest of the project into /app directory
# except for things in .dockerignore
COPY . .

# runs "npm run build" inside the docker image
# creates /dist folder
# compiles, bundles and minifies app for production
RUN npm run build


# STAGE 2: serve with nginx

# get nginx image
FROM nginx:alpine

# copy nginx.conf file into the image where nginx looks for it
COPY nginx.conf /etc/nginx/templates/default.conf.template

# copy the built files (called "build" in first stage) into the nginx stage
# /usr/share/nginx/html is nginx's "public" folder
COPY --from=build /app/dist /usr/share/nginx/html

# nginx listens on port 80 inside the container
EXPOSE 80

# no CMD needed (nginx image already starts nginx by default when container runs)

