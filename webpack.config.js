const path = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');
const HtmlWebpackPluginConfig = new HtmlWebpackPlugin({
	template: './src/main/js/index.html',
	filename: 'index.html',
	inject: 'body'
});

module.exports = {
	entry: './src/main/js/index.js',
	devtool: 'inline-source-map',
	cache: true,
	output: {
		path: __dirname,
		filename: './src/main/resources/static/built/bundle.js'
	},
	module: {
		loaders: [
			{test: /\.js$/, loader: 'babel-loader', exclude: /node_modules/},
			{test: /\.jsx$/, loader: 'babel-loader', exclude: /node_modules/}
		]
	},
	plugins: [HtmlWebpackPluginConfig]
};
