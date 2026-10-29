# AWS EC2 deployment checklist

1. Launch Ubuntu 22.04/24.04 EC2. In the security group, allow SSH only from your IP and allow HTTP/HTTPS as needed. Do not open port 3306.
2. Install Docker Engine and Docker Compose plugin using the current official Docker instructions for Ubuntu.
3. Clone the repository and create a server-side `.env` file with strong unique passwords and a JWT secret.
4. Set `CORS_ALLOWED_ORIGINS` to the public frontend origin if the browser accesses the API cross-origin. With the provided Nginx reverse proxy, same-origin `/api` is preferable.
5. Run `docker compose up -d --build`; verify `curl http://localhost:8080/actuator/health`.
6. Configure DNS and HTTPS using a certificate managed by Certbot or an AWS Application Load Balancer.
7. Enable automatic security updates, database backups, logs/monitoring, and restrict access to secrets.
8. Change demo account passwords and remove or disable demo seed accounts before a public demonstration.

The included pipeline deliberately does not execute a remote deployment command until you configure a secure SSH/SSM strategy. Never commit private keys, registry tokens, or production `.env` files.
