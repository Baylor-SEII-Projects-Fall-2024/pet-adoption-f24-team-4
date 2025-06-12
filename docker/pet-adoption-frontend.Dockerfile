# Use ARM-compatible Node image for build
FROM arm32v7/node:20 AS build
WORKDIR /build
COPY . .

RUN yarn install --network-timeout 1000000
RUN yarn run build

# Runtime image
FROM arm32v7/node:20
WORKDIR /app
COPY --from=build /build .

# Run the app
ENTRYPOINT exec yarn start
