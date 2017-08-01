'use strict';

const express = require('express');

// Constants
const PORT = 8080;
const HOST = '0.0.0.0';

// App
const app = express();
app.get('/', (req, res) => {
  	var name = 'Arun';

  	console.log('name[id]: ' + req.query['id']);
  	
	if (req.query['id'] == '1') {
		name = 'Sheldon';
	}

  res.send(name);
});

app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);

