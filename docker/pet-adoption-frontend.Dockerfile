# Use ARM-compatible Node image for build
FROM arm32v7/node:20 AS build
WORKDIR /build
COPY . .

RUN apt-get update && apt-get install -y python3 make g++ && rm -rf /var/lib/apt/lists/*
RUN yarn install --network-timeout 300000 --ignore-engines --prefer-offline
RUN yarn run build

# Runtime image
FROM arm32v7/node:20
WORKDIR /app
COPY --from=build /build .

# Run the app
ENTRYPOINT exec yarn start
