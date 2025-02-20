var exec = require('cordova/exec');

exports.pickFile = function(success, error) {
    exec(success, error, 'FilePicker', 'pickFile', []);
};
