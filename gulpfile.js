const gulp = require('gulp');
const del = require('del');
const browserify = require('browserify');
const source = require('vinyl-source-stream');
const jest = require('gulp-jest').default;

const paths = {
	mainSrc: './src/main/js/main.es6',
	compiledJsDir: './src/main/resources/static/js/'
};

gulp.task('clean', function() {
	return del([paths.compiledJsDir]);
});

gulp.task('jest', function () {
	return gulp.src('.')
		.pipe(jest({
		config: {
			"transform": {
				".*": "<rootDir>/node_modules/babel-jest"
			},
			"setupTestFrameworkScriptFile": "./node_modules/jest-enzyme/lib/index.js",
			"moduleFileExtensions": [
				"js",
				"json",
				"es6"
			]
		}
	}));
});

gulp.task('build', ['clean'], function() {
	return browserify({
		extensions: ['.es6'],
		entries: paths.mainSrc
	})
	.transform('babelify', {presets: ["es2015", "react"]})
	.bundle()
	.on('error', function (err) { console.log('Error : ' + err.message); })
	.pipe(source('main.js'))
	.pipe(gulp.dest(paths.compiledJsDir));
});