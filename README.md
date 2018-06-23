This aplication was developed in Java SE (a.k.a. Core) with the purpose of keeping track of devices (scales) and assert if their state is in accordance with the state precision laws for scales as customers may sue the company if they are being overcharged if the weight displayed differs from the real weight of the product.

This app was developed for Windows systems  and requires Java 6.0 or above. A MySQL database maintained by my employer hosts the database. The format of the GUI was made to reflect the resolution of the tablet in which it was to be implemented on.

Description of contents:\n
Scale Inventory: contains all files necessary for the application to run (further description below)
ScalePM: contains the project files such as binarys and Java scripts



The app contains 5 panels: (1) Crreate a Preventive Maintenance record (2) Generate Excel records (3) CRUD scale data (4) CRUD worstation data (a scale is assigned to a workstation for tracking purposes) (5) CRUD repair records

Please refer to the file READ ME.docx for all intructions in all the functionality of the app.

To start using the app, download the directory 'Scale Inventory' and place it on the 'C:\" location in your drive. To start the application, simply double-click the jar file 'Scale Inventory Manager 2.4.1.jar' within the 'Scale Inventory' directory. If app doesn't load data, the cause is related to domain constraints.

In terms of development, the app contains 4 classes: (1)ScalePMMain starts the GUI and loads main components (2)ScalePMGUI contains GUI elements and processes business layer data (3) ScalePMDB proccesses DB transactions between the GUI and the DB (4) ScalePMIO handles the file writting
Note that due to time constraints, and since I was only allowed to work on this project during down-time, the best coding practices weren't applied such as (1) using methods with a signle purpose (2) decrease coupling between classes (3) extensive use of comments (4) transfer DB Resultsets from one class to another

 An Aglie methodology approach was used on this project:
Phase 1 had a 3 months duration (May-July 2017) and it entailed only functionality for creating preventive maintenance records and create excel reports ( the first 2 panels). The database was a local MS Access DB. Management agreed to implement a 2nd phase after showing the results.  
Phase 2 had a 2 Months duration (August-September 2017) and it entailed the building of panels 3, 4 and 5 for managing Scale data, workstation data and repair records, respectively. The database was hosted through AWS RDS on a MYSQL environment, later management asked me to find an alternative solution to AWS.
Phase 3 had a 1 week duration (September 2017) and it entailed migrating the database from AWS to a company hosted server maintained for analytic purposes.

For inquiries, please contact NCorreia1@my.devry.edu.

Built for FreshDirect  Plant Technology Services team.


Nuno Correia
                                                     




# Scale-Inventory-Manager-2.4
