CNAB File Processor

This application is able to receive CNAB files with specific formatting (defined by the template), process it and persist on a H2 database and confirm the processing of this file by returning a JSON containing some of the processed information.
Or the API will return a JSON describing file format errors.
Or, instead, the application will return a JSON containing error message and, when necessary, it`ll be followed by erros describing what is wrong with the information inside the file.

The API is accessible by the following URL: http://127.0.0.1:9001/CNABFileUpload for sending CNAB files for validation.
And, there is the following URL http://localhost:9001/cnabFile in order to list all valid processed files.
The way you`ll send files is using POST verb, informing that the parameter representing the file is named as "cnabFile".
To consult the valid persisted files, use GET verb, calling the /cnabFile path.

Once the file is processed, a register will be persisted in a database, in order to allow late queries.
Only the valid file will be persisted.

Documentation
You can consult rich information by accessing http://localhost:9001/swagger-ui/index.html#/

Future implementations:
- Save a copy of the document: this API is persisting the file information, not the file itself
- Insert this API inside a container: This API is not ready to face scalability or high availability as it is built. It needs to be combined with a container, to allow the horizontal scalability and improve performance.

How to use this API:
1 - This API is setted to run on port 9001 so, it`s mandatory to call this API using it`s URL or IP address and point to the 9001 port.
2 - To send a CNAB file for validation and processing, it`s needed to send a POST request to the following address: http://[IP_ADDRESS OR URL]:9001/CNABFileUpload
3 - The request must contain a file and the corresponding parameter must have the cnabFile name.
4 - Once the POST request is sent, the API will firstly read and validate the file format, according to the specified template.
5 - Then, the registries will be readed and validated.
6 - As the validation is being performed, each registry will be persisted
7 - After all validations, the file itself will be persisted.
0 - After all these steps, the corresponding information will be sent to the requester. Independent of the result, the corresponding information will return.