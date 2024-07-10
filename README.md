# issue-41811-reproducer

This is a reproducer for Quarkus [issue 41811](https://github.com/quarkusio/quarkus/issues/41811).

Run below steps to reproduce it:
1. Start the service with `mvn quarkus:dev`
2. Run `curl -v -XPOST http://localhost:8080/download/create` to create a test file
3. Run `curl -v -XGET http://localhost:8080/download` to download the test file
4. Run `lsof /tmp/test.txt` and observe open file handle:
   ```
   lsof: WARNING: can't stat() tracefs file system /sys/kernel/debug/tracing
         Output information may be incomplete.
   COMMAND    PID    USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
   java    484529 REDACTED  420r   REG   0,40        0 3900 /tmp/test.txt
   ```
5. Run `curl -v -XDELETE http://localhost:8080/download/delete` to delete the file
6. (Optional) Observe `.nfsxxxxxxxxx` file creation after step 5 if NFS filesystem is used.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

