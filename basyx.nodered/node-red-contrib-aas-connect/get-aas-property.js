module.exports = function(RED) {

    const request = require('request');

    function GetAASProperty(config) {
        let property = config.property || 'temp';
        let endpoint = `http://aas-wrapper:6500/streamsheets/${property}`;
        let period = Number(config.period) || 1;
        let msperiod = period * 1000;

        RED.nodes.createNode(this, config);

        let node = this;
        let interval;
        node.on('input', function(msg, send, done) {
            interval = setInterval(() => {
                request(endpoint, { json: true }, (err, res, body) => {
                    if (err) {
                        node.error(err);
                        if (done) done(err);
                        return;
                    }

                    if (!body.success) {
                        node.error(body.messages);
                        if (done) done(body.messages);
                        return;
                    }

                    // let mostRecentTimestamp = body.timestamp.pop();
                    // let mostRecentValue = body.content.pop();

                    let data = {
                        tstamp: body.timestamp,
                        data: body.content,
                    }
                    
                    msg.payload = data;

                    // For maximum backwards compatibility, check that send exists.
                    // If this node is installed in Node-RED 0.x, it will need to
                    // fallback to using `node.send`
                    send = send || function() { node.send.apply(node, msg) }
                    send(msg);

                    if (done) done();
                });
            }, msperiod);

        });

        node.on('close', function(done) {
            clearInterval(interval);
            if (done) done();
        });

    }

    RED.nodes.registerType('get-aas-property', GetAASProperty);
}