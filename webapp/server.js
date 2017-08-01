'use strict';

const express = require('express');

var request = require('sync-request');

// Constants
const PORT = 8080;
const HOST = '0.0.0.0';

// App
const app = express();
app.get('/', (req, res) => {
  res.send(get("http://localhost:49160") 
  	+ ' '  
  	+ get("http://localhost:49161"));
});

function get(url) {
	return require('sync-request')('GET', url).getBody();

}

app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);

