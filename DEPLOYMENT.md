# Deployment Guide for Render

This guide explains how to deploy the Employee Management System on Render using Docker.

## Prerequisites

- A Render account
- PostgreSQL database (already configured with the provided connection string)
- Git repository with the application code

## Database Configuration

The application is configured to use the following PostgreSQL database:
- **Host**: dpg-d2au0jruibrs73f28550-a.oregon-postgres.render.com
- **Database**: empdb_io87
- **Username**: empdb_io87_user
- **Password**: 8KXr0sAYIUyZwC3ySoWfhnV9qykf9tYb

## Deployment Steps

### Option 1: Using render.yaml (Recommended)

1. **Push your code to a Git repository** (GitHub, GitLab, etc.)

2. **Connect to Render**:
   - Go to [render.com](https://render.com)
   - Sign in to your account
   - Click "New +" and select "Blueprint"

3. **Deploy using Blueprint**:
   - Connect your Git repository
   - Render will automatically detect the `render.yaml` file
   - Click "Apply" to deploy

### Option 2: Manual Deployment

1. **Create a new Web Service**:
   - Go to Render Dashboard
   - Click "New +" → "Web Service"
   - Connect your Git repository

2. **Configure the service**:
   - **Name**: empcursodemo
   - **Environment**: Docker
   - **Region**: Choose closest to your users
   - **Branch**: main (or your default branch)
   - **Build Command**: `docker build -t empcursodemo .`
   - **Start Command**: `docker run -p $PORT:8080 empcursodemo`

3. **Environment Variables** (optional):
   - `SPRING_PROFILES_ACTIVE`: production
   - `DATABASE_URL`: jdbc:postgresql://dpg-d2au0jruibrs73f28550-a.oregon-postgres.render.com/empdb_io87
   - `DATABASE_USERNAME`: empdb_io87_user
   - `DATABASE_PASSWORD`: 8KXr0sAYIUyZwC3ySoWfhnV9qykf9tYb
   - `ADMIN_USERNAME`: admin
   - `ADMIN_PASSWORD`: admin123

4. **Deploy**:
   - Click "Create Web Service"
   - Render will build and deploy your application

## Local Development

To run the application locally:

1. **Build the Docker image**:
   ```bash
   docker build -t empcursodemo .
   ```

2. **Run the container**:
   ```bash
   docker run -p 8080:8080 empcursodemo
   ```

3. **Access the application**:
   - Open http://localhost:8080 in your browser
   - Login with admin/admin123

## Application Features

- **Employee Management**: CRUD operations for employees
- **User Authentication**: Secure login system
- **REST API**: Available at `/api/employees`
- **Web Interface**: Thymeleaf-based UI
- **Database**: PostgreSQL with automatic schema creation

## Troubleshooting

### Common Issues

1. **Database Connection Failed**:
   - Verify the database credentials
   - Check if the database is accessible from Render's servers

2. **Build Fails**:
   - Ensure all dependencies are properly defined in `pom.xml`
   - Check that the Dockerfile is in the root directory

3. **Application Won't Start**:
   - Check the logs in Render Dashboard
   - Verify the port configuration (should use `$PORT` environment variable)

### Logs

View application logs in the Render Dashboard:
1. Go to your service
2. Click on "Logs" tab
3. Check for any error messages

## Security Notes

- Change default admin credentials in production
- Use environment variables for sensitive data
- Consider enabling HTTPS for production deployments
- Regularly update dependencies for security patches
