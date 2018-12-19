FROM scratch
ADD hello /hello
ENTRYPOINT ["echo"]
CMD ["hello"]