var gulp = require('gulp');
var del = require('del');
var browserify = require('browserify');
var source = require('vinyl-source-stream');
var jest = require('gulp-jest').default;

var paths = {
	es6Src: './src/main/resources/static/js_src/**/*.{es6,jsx}',
	mainSrc: './src/main/resources/static/js_src/main.es6',
	compiledJsDir: './src/main/resources/static/js/',
	// scssFiles: './sass/**/*.scss',
	// cssDir: './css/',
	mainCompile: './src/main/resources/static/js/main.js',
	testSrc: './test/js/'
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