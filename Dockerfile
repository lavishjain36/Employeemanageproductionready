# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the Maven wrapper and pom.xml
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .

# Make mvnw executable
RUN chmod +x mvnw

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Create a non-root user
RUN addgroup --system javauser && adduser --system --ingroup javauser javauser

# Change ownership of the app directory
RUN chown -R javauser:javauser /app

# Switch to non-root user
USER javauser

# Expose port
EXPOSE 8080

# Set environment variables
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Run the application
CMD ["sh", "-c", "java $JAVA_OPTS -jar target/empcursodemo-0.0.1-SNAPSHOT.jar"]
