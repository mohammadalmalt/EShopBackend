# AWS Basics and Quick Project Guide

## 1. Basic Principle:
In AWS, the main idea is to operate infrastructure **on demand**.

You pay for what you need (computing, databases, storage, etc.) and can **scale up or down** as you wish.

## 2. Important AWS Services for Running Web Applications:

| Service                  | Purpose                        | Quick Info                               |
| ------------------------- | ------------------------------ | ---------------------------------------- |
| EC2                       | Virtual servers                | Web server, Backend                     |
| S3                        | File storage                   | Images, Backups, Files                  |
| RDS                       | Relational databases           | MySQL, PostgreSQL, Oracle, etc.          |
| Elastic Beanstalk         | Platform as a Service (PaaS)   | Deployment without server fiddling      |
| CloudWatch                | Monitoring and logging         | Alerts, Metrics, Logs                   |
| IAM                       | User/Role management           | Who is allowed to do what?              |
| VPC                       | Virtual private network        | Firewall, IP Management                 |
| Route53                   | DNS management                 | Domains, Load Balancer Routing          |
| ALB (Application Load Balancer) | Load balancing             | Distributes requests across servers     |
| Lambda                    | Serverless functions           | For small tasks without own servers     |

## 3. Typical Architecture for Your Skills (Java Backend + Angular SPA):

- **Client (Browser)**  
  → **Angular SPA** (stored on S3 + distributed via CloudFront CDN)  
  → calls **Java Backend** (REST-API on EC2 or Elastic Beanstalk)  
  → connects to **Database** (RDS)

### Optionally with Load Balancer & Auto Scaling:

- **Client** → **ALB** → **EC2 (Java Backend)** → **RDS**

## 4. Deployment:

### Backend (Java Spring Boot):
- As a **Docker container** on **ECS (Elastic Container Service)** or simply as **WAR/JAR** on **EC2** / **Elastic Beanstalk**.

### Frontend (Angular):
```bash
ng build --prod
```


- Upload the output to an **S3 Bucket** and distribute it via **CloudFront** (for fast global delivery).

## 5. Monitoring & Operations:
- Send logs to **CloudWatch**.
- Configure alerts (e.g., when CPU > 80% or instance dies).
- Enable backups for **RDS**.
- Set up **Security Groups** properly (e.g., open port 80/443 for web access, database ports only for internal use).

## 6. Important Tips for Getting Started:
- Assign **IAM roles** carefully (never give full admin rights).
- Set **resource tags** (e.g., `Environment=Dev`, `Owner=Momo`) to make cleanup easier later.
- Think about **backup and recovery** early (S3 versioning, RDS snapshots).

# 🔥 Your Quick Setup Plan for a Real Project:

- Create an **IAM user** → get your own access credentials.
- Create an **S3 bucket** → upload Angular App.
- Set up an **Elastic Beanstalk environment** → deploy Java Spring Boot App.
- Create an **RDS instance** → set up your database.
- Adjust **Security Groups** → only open necessary ports.
- Create **CloudWatch alarms** → for CPU, RAM, and error logs.
- **Optional:** Buy and set up a domain with **Route53**.

# 📦 Example Project: Spring Boot Backend on EC2

## 1. Create EC2 Instance
- AWS Management Console → EC2 → "Launch Instance".
- Choose **Amazon Linux 2** or **Ubuntu** image.
- **T2.micro** is sufficient to start (Free Tier).

**Security Group Settings:**
- Port 22 (SSH) open for your IP.
- Port 8080 (Spring Boot default port) open for everyone or internal use.
- Port 80 (for HTTP, if desired).

## 2. Install Java
SSH into your instance:

### For **Amazon Linux**:
```bash
sudo yum update -y sudo yum install java-17-amazon-corretto -y
```
### For **Ubuntu**:
```bash
sudo apt update sudo apt install openjdk-17-jdk -y
```

###  On your local machine:
```bash
scp -i "your-key.pem" your-project.jar ec2-user@EC2-IP:/home/ec2-user/
```


## 4. Start the App
Login to your EC2 instance:
```bash
java -jar your-project.jar
```


Check in your browser:  
`http://EC2-IP:8080`

## 5. (Optional) Run in Background with `screen` or `tmux`
Install `screen`:
```bash
sudo yum install screen -y
```

Start a screen session:
```bash
screen java -jar your-project.jar
```

Detach from screen (leave app running):
- Press `CTRL+A`, then `D`.

## 6. (Optional) Elastic IP
- Reserve and assign an **Elastic IP** if you want a fixed public IP address for your EC2 instance.

# 🛡 Extras for a Clean Setup:
- Set up an **SSL certificate** (e.g., via ACM + ALB).
- Configure **CloudWatch Logs**.
- Use **S3** for static files + host the Angular frontend.
- Use **RDS** for proper database separation instead of running DB locally on EC2.

