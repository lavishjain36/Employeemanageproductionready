# CI/CD Pipeline with Code Analysis

This project includes a comprehensive CI/CD pipeline with automated code analysis and reporting capabilities.

## 🚀 Features

### Code Analysis Tools
- **JaCoCo**: Code coverage analysis
- **SpotBugs**: Static analysis for bug detection
- **PMD**: Code quality and best practices analysis
- **Checkstyle**: Code style and formatting checks

### Reporting
- **HTML Reports**: Detailed web-based reports for each tool
- **Excel Reports**: Comprehensive Excel spreadsheets with all analysis data
- **GitHub Actions Artifacts**: All reports stored as downloadable artifacts

### CI/CD Pipeline
- **Automated Testing**: Runs on every push and pull request
- **Code Quality Gates**: Ensures code meets quality standards
- **Docker Build**: Automated Docker image creation
- **Deployment Ready**: Prepared for deployment to Render

## 📋 Prerequisites

- GitHub repository
- Java 17
- Maven
- Python 3.9+ (for Excel report generation)

## 🛠️ Setup Instructions

### 1. GitHub Repository Setup

1. **Push your code to GitHub**:
   ```bash
   git add .
   git commit -m "Add CI/CD pipeline with code analysis"
   git push origin main
   ```

2. **Enable GitHub Actions**:
   - Go to your repository on GitHub
   - Navigate to Settings → Actions → General
   - Enable "Allow all actions and reusable workflows"

### 2. Local Development Setup

1. **Install Maven plugins** (already configured in pom.xml):
   ```bash
   ./mvnw clean install
   ```

2. **Run code analysis locally**:
   ```bash
   # Run all analysis tools
   ./mvnw clean test jacoco:report spotbugs:report pmd:pmd checkstyle:checkstyle
   
   # Generate Excel report (requires Python)
   python scripts/generate_excel_report.py
   ```

## 📊 Understanding the Reports

### JaCoCo Coverage Report
- **Location**: `target/site/jacoco/index.html`
- **What it shows**: Line coverage, branch coverage, and complexity metrics
- **Goal**: Achieve >80% line coverage

### SpotBugs Report
- **Location**: `target/spotbugs.html`
- **What it shows**: Potential bugs, security vulnerabilities, and performance issues
- **Severity Levels**: High, Medium, Low

### PMD Report
- **Location**: `target/pmd.html`
- **What it shows**: Code quality violations, best practices, and design issues
- **Categories**: Performance, Security, Code Style, Design

### Checkstyle Report
- **Location**: `target/checkstyle.html`
- **What it shows**: Code style violations and formatting issues
- **Standards**: Google Java Style Guide

### Excel Report
- **Location**: `reports/code-analysis-report.xlsx`
- **What it shows**: Comprehensive summary of all analysis tools
- **Sheets**: Summary, Coverage Details, SpotBugs Details, PMD Details, Checkstyle Details

## 🔧 Configuration

### Maven Plugins Configuration

The following plugins are configured in `pom.xml`:

#### JaCoCo Configuration
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
</plugin>
```

#### SpotBugs Configuration
```xml
<plugin>
    <groupId>com.github.spotbugs</groupId>
    <artifactId>spotbugs-maven-plugin</artifactId>
    <version>4.8.2.0</version>
</plugin>
```

#### PMD Configuration
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-pmd-plugin</artifactId>
    <version>3.21.2</version>
</plugin>
```

#### Checkstyle Configuration
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>3.3.1</version>
</plugin>
```

### Customizing Analysis Rules

#### Checkstyle Rules
Edit `checkstyle.xml` to customize code style rules:
- Naming conventions
- Import statements
- Code formatting
- Method and class size limits

#### PMD Rules
PMD uses default rules, but you can customize by adding a `ruleset.xml` file.

#### SpotBugs Rules
SpotBugs uses default bug patterns. You can exclude specific patterns in the plugin configuration.

## 🚀 GitHub Actions Workflow

### Workflow Triggers
- **Push**: Runs on pushes to `main` and `develop` branches
- **Pull Request**: Runs on all PRs to `main` branch
- **Manual**: Can be triggered manually via GitHub Actions UI

### Jobs

#### 1. Test and Analyze
- Runs unit tests
- Generates code coverage reports
- Performs static analysis
- Uploads HTML reports as artifacts

#### 2. Generate Excel Report
- Downloads test results
- Generates comprehensive Excel report
- Uploads Excel report as artifact

#### 3. Build and Deploy
- Builds the application
- Creates Docker image
- Ready for deployment

#### 4. Notify
- Provides pipeline status notifications

## 📈 Quality Gates

### Recommended Thresholds
- **Code Coverage**: >80% line coverage
- **SpotBugs**: 0 high-priority bugs
- **PMD**: 0 high-priority violations
- **Checkstyle**: 0 error-level issues

### Setting Quality Gates

Add these to your `pom.xml` to enforce quality gates:

```xml
<!-- JaCoCo Quality Gate -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <executions>
        <execution>
            <id>jacoco-check</id>
            <goals>
                <goal>check</goal>
            </goals>
            <configuration>
                <rules>
                    <rule>
                        <element>BUNDLE</element>
                        <limits>
                            <limit>
                                <counter>LINE</counter>
                                <value>COVEREDRATIO</value>
                                <minimum>0.80</minimum>
                            </limit>
                        </limits>
                    </rule>
                </rules>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## 🔍 Viewing Reports

### GitHub Actions Artifacts
1. Go to your repository on GitHub
2. Navigate to Actions tab
3. Click on a workflow run
4. Scroll down to "Artifacts"
5. Download the reports you want to view

### Local Reports
After running analysis locally:
- **HTML Reports**: Open in your web browser
- **Excel Report**: Open with Excel or Google Sheets

## 🐛 Troubleshooting

### Common Issues

#### 1. Maven Plugin Failures
```bash
# Clean and rebuild
./mvnw clean install

# Run with debug output
./mvnw clean test -X
```

#### 2. Python Script Issues
```bash
# Install dependencies
pip install -r scripts/requirements.txt

# Run with verbose output
python -v scripts/generate_excel_report.py
```

#### 3. GitHub Actions Failures
- Check the Actions tab for detailed error logs
- Ensure all required files are committed
- Verify Maven wrapper files are present

### Performance Optimization

#### 1. Caching
The workflow uses Maven caching to speed up builds:
- Dependencies are cached between runs
- Only changed dependencies are downloaded

#### 2. Parallel Execution
- Tests and analysis run in parallel where possible
- Excel report generation runs after analysis completes

## 📚 Additional Resources

- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
- [SpotBugs Documentation](https://spotbugs.readthedocs.io/)
- [PMD Documentation](https://pmd.github.io/)
- [Checkstyle Documentation](https://checkstyle.org/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)

## 🤝 Contributing

When contributing to this project:

1. **Follow the code style**: Run Checkstyle before committing
2. **Write tests**: Ensure good code coverage
3. **Check for bugs**: Run SpotBugs analysis
4. **Follow best practices**: Address PMD violations

## 📄 License

This CI/CD setup is part of the Employee Management System project.
