FROM node:16.13.1-alpine AS build
COPY . .
RUN npm install
RUN npm run build

FROM nginx:1.21.0-alpine AS runtime
COPY --from=build /build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]