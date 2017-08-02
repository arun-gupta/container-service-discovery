'use strict';

const express = require('express');

var request = require('sync-request');

// Constants
const PORT = 8080;
const HOST = '0.0.0.0';

// App
const app = express();
app.get('/', (req, res) => {
    var args = process.argv.slice(2);

    var greeter = "http://" + args[0] + ":" + args[1] + "?greet=" + req.query['greet'];
    var name = "http://" + args[2] + ":" + args[3] + "?id=" + req.query['id'];
    console.log('greeter: ' + greeter);
    console.log('name: ' + name);

    res.send(get(greeter) + ' ' + get(name));
});

function get(url) {
	return require('sync-request')('GET', url).getBody();
}

app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);

