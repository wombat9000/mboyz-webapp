const path = require('path');

module.exports = {
	entry: './src/index.js',
	devtool: 'inline-source-map',
	output: {
		filename: 'bundle.js',
		path: path.resolve(__dirname, 'dist')
	},
	module: {
		loaders: [
			{ test: /\.js$/, loader: 'babel-loader', exclude: /node_modules/ },
			{ test: /\.jsx$/, loader: 'babel-loader', exclude: /node_modules/ }
		]
	}
};