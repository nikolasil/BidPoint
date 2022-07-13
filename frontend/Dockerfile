# stage1 - build react app first 
FROM node:16.13.1-alpine as build
# Set working directory
WORKDIR /app
# Copy all files from current directory to working dir in image
COPY . .
# install node modules and build assets
RUN yarn install

EXPOSE 80

ENTRYPOINT ["npm", "start"]

# RUN yarn build

# # stage 2 - build the final image and copy the react build files
# # nginx state for serving content
# FROM nginx:alpine
# # Set working directory to nginx asset directory
# WORKDIR /usr/share/nginx/html
# # Remove default nginx static assets
# RUN rm -rf ./*
# # Copy static assets from build stage
# COPY --from=build /app/build .
# # Containers run nginx with global directives and daemon off
# EXPOSE 80
# ENTRYPOINT ["nginx", "-g", "daemon off;"]