# ==========================================
# Tahap 1: Builder
# Menggunakan Node.js Alpine untuk kompilasi kode React
# ==========================================
FROM node:alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# ==========================================
# Tahap 2: Runner
# Menggunakan Nginx Alpine untuk menyajikan aset statis
# ==========================================
FROM nginx:alpine
# Menghapus konfigurasi default bawaan Nginx
RUN rm /etc/nginx/conf.d/default.conf
# Menyalin konfigurasi custom Nginx
COPY nginx/default.conf /etc/nginx/conf.d/

# Menyalin folder hasil kompilasi dari tahap builder
COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]