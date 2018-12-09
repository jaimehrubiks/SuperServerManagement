# Important Note
This is a free topic project for a class course at Illinois Institute of Techonology. It is not meant to be used by other thing than for learning purposes.

# Description
This project materializes a complete product which aims to help system operators manage a set of computer servers. The first step to setup the product, is to install the **master** server on a remote computer, which needs to have a mysql database installed, and needs to be publicly accessed through the internet on TCP ports 8000 and 8001. The manager must also preinstall what will be called a **minion**, which is a tiny software that connects back to the master, on each of those servers that he wants to manage.

Then, the system administrator will launch a GUI application, called the **user**, on his personal computer. This software will connect to the master and retrieve a list of his minions, whether they are online or not. The software will display basic information on each minion, or stored data in case it is not online. The software will also allow to perform basic operations on those minions, such as requesting for the process list on them, when they are online.

Finally, if the user who uses the GUI application is an admin to the system (there are two types of accounts who can access the gui software), we will be able to perform additional operations, such as view a complete list of events triggered by users and minions.

![](/docs/itmd510.png)


# Running
Execute the master

*java -jar ssm.jar master*

Execute one minion

*java -jar ssm.jar minion*

Execute the demo GUI (a demo proof of concept, very basic)

*java -jar ssm.jar user*


## Using the full GUI

Execute a full featured GUI (MVC in javaFX)

*Download from this repo:* [SuperServerManagementGUI](https://github.com/jaimehrubiks/SuperServerManagementGui)

![](/docs/gui.png)
