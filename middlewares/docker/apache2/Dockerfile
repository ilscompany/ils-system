FROM ubuntu:14.04

MAINTAINER lonceny_mara <mlonceny@gmail.com>

RUN apt-get update && apt-get install -y dialog apache2

# Define default command
CMD ["apachectl", "-D", "FOREGROUND"]

# Expose ports
EXPOSE 80 443