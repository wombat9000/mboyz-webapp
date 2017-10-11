// squelch warning about polyfill from jest
global.requestAnimationFrame = callback => setTimeout(callback, 0);