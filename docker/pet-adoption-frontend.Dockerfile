# Base build image with tools for native deps
FROM arm32v7/node:20 AS build

WORKDIR /build
COPY . .


# Install dependencies with long timeout
RUN yarn install --network-timeout 300000 --ignore-engines --prefer-offline

# Build the project
RUN yarn run build

# Runtime image
FROM arm32v7/node:20
WORKDIR /app
COPY --from=build /build .

# Run the app
CMD ["yarn", "start"]
