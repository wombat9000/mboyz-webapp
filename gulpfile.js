var gulp = require('gulp');
var react = require('gulp-react');
var babel = require('gulp-babel');
var concat = require('gulp-concat');
var del = require('del');
var browserify = require('browserify');
var source = require('vinyl-source-stream');


var paths = {
	es6Src: './src/main/resources/static/js_src/**/*.{es6,jsx}',
	mainSrc: './src/main/resources/static/js_src/main.es6',
	compiledJsDir: './src/main/resources/static/js/',
	// scssFiles: './sass/**/*.scss',
	// cssDir: './css/',
	mainCompile: './src/main/resources/static/js/main.js'
};

gulp.task('clean', function() {
	return del([paths.compiledJsDir]);
});

gulp.task('build', ['clean', 'concat'], function() {
	return browserify(paths.mainCompile)
		.transform('babelify', {presets: ["es2015", "react"]})
		.bundle()
		.on('error', function (err) { console.log('Error : ' + err.message); })
		.pipe(source('main.js'))
		.pipe(gulp.dest(paths.compiledJsDir));
});

gulp.task('concat', function() {
	return gulp.src(paths.es6Src)
		.pipe(concat('main.js'))
		.pipe(gulp.dest(paths.compiledJsDir));
});