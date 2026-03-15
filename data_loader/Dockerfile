FROM python:3.14.3-slim-bookworm

ENV PYTHONDONTWRITEBYTECODE=1 \
    PYTHONUNBUFFERED=1 \
    TZ=Europe/Madrid

WORKDIR /app

RUN apt-get update && apt-get install -y --no-install-recommends \
    cron \
    tzdata \
    curl \
    ca-certificates \
    gnupg \
    unixodbc \
    unixodbc-dev \
    libgssapi-krb5-2 \
    g++ \
    && rm -rf /var/lib/apt/lists/*

# Repositorio de Microsoft + ODBC Driver 18
RUN curl -sSL -O https://packages.microsoft.com/config/debian/12/packages-microsoft-prod.deb \
    && dpkg -i packages-microsoft-prod.deb \
    && rm packages-microsoft-prod.deb \
    && apt-get update \
    && ACCEPT_EULA=Y apt-get install -y --no-install-recommends msodbcsql18 \
    && rm -rf /var/lib/apt/lists/*

# Instala dependencias Python
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copia el script y el dotenv
COPY loader.py .
COPY .env .

# Ejecuta loader.py una vez al día a las 00:00
RUN printf '0 0 * * * root cd /app && /usr/local/bin/python /app/loader.py >> /proc/1/fd/1 2>> /proc/1/fd/2\n' > /etc/cron.d/loader-cron \
    && chmod 0644 /etc/cron.d/loader-cron \
    && crontab /etc/cron.d/loader-cron

CMD ["cron", "-f"]