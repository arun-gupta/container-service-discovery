'use strict';

const express = require('express');

// Constants
const PORT = 8080;
const HOST = '0.0.0.0';

// App
const app = express();
app.get('/*', (req, res) => {
  	var greet = 'Hello';

  	console.log('greet[greet]: ' + req.query['greet']);

	if (req.query['greet'] == 'ho') {
		greet = 'Howdy';
	}

  res.send(greet);
});

app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);

