const path = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');
const HtmlWebpackPluginConfig = new HtmlWebpackPlugin({
	template: './src/index.html',
	filename: 'index.html',
	inject: 'body'
});

module.exports = {
	entry: './src/index.js',
	devtool: 'inline-source-map',
	output: {
		filename: 'bundle.js',
		path: path.resolve(__dirname, 'dist')
	},
	module: {
		loaders: [
			{test: /\.js$/, loader: 'babel-loader', exclude: /node_modules/},
			{test: /\.jsx$/, loader: 'babel-loader', exclude: /node_modules/}
		]
	},
	plugins: [HtmlWebpackPluginConfig]
};
