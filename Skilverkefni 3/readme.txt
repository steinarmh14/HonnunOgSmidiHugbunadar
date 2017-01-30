REST API backend implemented with nodejs, express and mongodb

***Instructions on how to run project***
1)
    - Visit: https://nodejs.org/en/ and download nodejs
    - Open: Terminal/command window
    - Install: npm with 'npm install'
    - Install: express with 'npm install --save express'
    - Install: mongodb with 'npm install --save mongodb'
    - Install: mongoose with 'npm install --save mongoose'
    - Install: body-parser with 'npm install --save body-parser'
2)
    - Open: Terminal/command window and navigate to root project. Type: 'mongod --dbpath ./mongodb' to start up database
    - Open: Another terminal/command window and navigate to root project. Type: 'node index.js' to build project and open connection on localhost


***Instructions on how to run integration tests***
1)
    - Open: Terminal/command window
    - Install: mocha with 'npm install --save mocha' (You might need to install mocha globally as well with 'npm install -g mocha')
    - Install: supertest with 'npm install --save supertest'
    - Install: should with 'npm install --save should'
2)
    - Open: Open terminal/command window and navigate to root project. Type 'mongod --dbpath ./mongodb' to start up database
    - Open: Another terminal/command window and navigate to root project. Type: 'mocha' to run integration tests