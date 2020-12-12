FROM seudev/java-4dev:11

ENV HISTFILE=/root/.bash_history/.bash_history

COPY init-container /usr/local/bin/

RUN  chmod +x /usr/local/bin/init-container

CMD ["sleep", "infinity"]
