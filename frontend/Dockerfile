FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install --fetch-retries=5 --fetch-retry-factor=2 --fetch-retry-mintimeout=20000
COPY . .
RUN npm run build

FROM nginx:1.27-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/medicare/browser /usr/share/nginx/html
EXPOSE 80