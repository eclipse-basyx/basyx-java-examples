module.exports = function(RED) {

    const request = require('request');

    function SetAASProperty(config) {
        let property = config.property || 'temp';
        let endpoint = `http://aas-wrapper:6500/streamsheets/${property}`;

        RED.nodes.createNode(this, config);

        let node = this;
        node.on('input', function(msg, send, done) {
            let newValue = msg.payload;

            request({
                uri: endpoint,
                method: 'POST',
                json: true,
                body: { value: newValue }
            }, (err, res, body) => {
                if (err) {
                    node.error(err);
                    if (done) done(err);
                    return;
                }

                if (!body.success) {
                    node.error(body.error);
                    if (done) done(body.error);
                    return;
                }

                if (done) done();
            });
        });
    }

    RED.nodes.registerType('set-aas-property', SetAASProperty);

}