window.onload = () => {
	console.log('loaded');
}

new p5(sketchManager, 'canvas-wrapper');

function gcd(x, y) {
	if (y == 0) return x;
	return gcd(y, x % y);
}

function getUrlQueries() {
	let queryStr = window.location.search.slice(1);  // 文頭?を除外
	queries = {};

	// クエリがない場合は空のオブジェクトを返す
	if (!queryStr) {
		return queries;
	}

	// クエリ文字列を & で分割して処理
	queryStr.split('&').forEach((queryStr) => {
		// = で分割してkey,valueをオブジェクトに格納
		let queryArr = queryStr.split('=');
		queries[queryArr[0]] = queryArr[1];
	});

	return queries;
}

//お絵描き用のキャンバス
function sketchManager(p) {
	const OPERATION_MODE = {
			DRAW: 0,
			ERASER: 1,
			DRAG: 2
	};
	const STACK_MAX_SIZE = 10;
	const MAX_SCALE = 30;
	const MIN_SCALE = 0.05;
	let operationMode = OPERATION_MODE.DRAW;
	let renderer;
	let canvas;
	let originalImage;
	let undoStack = [];
	let redoStack = [];
	let scale = 1;
	let ratioW;
	let ratioH;
	let windowRatioW;
	let windowRatioH;
	let styleWidth;
	let styleHeight;
	let thickness = 8;
	let eraserThickness = 8;
	let dragCanvasDownPos = {x: null, y: null};
	let isPinching = false;
	let isPressed = false;
	let previousPinchDistance;
	let currentPinchDistance;
	let diffPinchDistance;

	// DOM
	const canvasContainer = document.getElementById('canvas-container');
	const canvasWrapper = document.getElementById('canvas-wrapper');
	const saveButton = document.getElementById('save-canvas')
	const focusCenterButton = document.getElementById('focus-center');
	const drawModeButton = document.getElementById('draw-mode');
	const eraserModeButton = document.getElementById('eraser-mode');
	const dragModeButton = document.getElementById('drag-mode');
	const undoButton = document.getElementById('undo');
	const redoButton = document.getElementById('redo');

	const sideMenu = document.getElementById('side-menu');
	const topMenu = document.getElementById('top-menu');
	const palettePenMenuContainer = document.getElementById('palette-pen-menu-container');
	const palettePenThickness = document.getElementById('palette-pen-thickness');
	const palettePenThicknessLabel = document.querySelector('label[for="palette-pen-thickness"]');

	const paletteEraserMenuContainer = document.getElementById('palette-eraser-menu-container');
	const paletteEraserThickness = document.getElementById('palette-eraser-thickness');
	const paletteEraserThicknessLabel = document.querySelector('label[for="palette-eraser-thickness"]');

	const viewOriginalImage = document.getElementById("viewOriginalImage");
	// const sendSketch = document.getElementById('send-sketch');

	// sendSketch.addEventListener('submit', send, false);

	// 基本の操作を無効
	function preventDefaultFunc(e) {
		e.preventDefault();
	}

	// スマホのピンチインアウトを無効
	function disablePinch(e) {
		if (e.touches && e.touches.length > 1) {
			e.preventDefault();
		}
	}


	document.addEventListener('touchmove', disablePinch, {passive: false});
	document.addEventListener('wheel', preventDefaultFunc, {passive: false});
	canvasContainer.addEventListener('touchmove', preventDefaultFunc, {passive: false});

	palettePenThickness.addEventListener('input', (e) => {
		palettePenThicknessLabel.innerText = e.target.value + 'px';
		thickness = e.target.value;
	});

	paletteEraserThickness.addEventListener('input', (e) => {
		paletteEraserThicknessLabel.innerText = e.target.value + 'px';
		eraserThickness = e.target.value;
	});

	saveButton.addEventListener('click', (e) => {
		// p.saveCanvas(canvas, 'test', 'png');
		const tmpCanvas = document.createElement('canvas');
		const a = document.createElement('a');
		const img = new Image();
		img.src = canvas.toDataURL();
		img.onload = () => {
			const backImg = new Image();
			backImg.src = viewOriginalImage.toDataURL();
			backImg.onload = () => {
				tmpCanvas.width = p.width;
				tmpCanvas.height = p.height;
				tmpCanvas.getContext('2d').drawImage(backImg, 0, 0);
				tmpCanvas.getContext('2d').drawImage(img, 0, 0);
				saveButton.href = tmpCanvas.toDataURL();
				a.href = tmpCanvas.toDataURL();
				a.download = 'test.png';
				a.click();
			}
		};
	});
	focusCenterButton.addEventListener('click', focusCenter);
	undoButton.addEventListener('click', undo);
	redoButton.addEventListener('click', redo);
	drawModeButton.addEventListener('click', drawMode);
	eraserModeButton.addEventListener('click', eraserMode);
	dragModeButton.addEventListener('click', dragMode);


	p.setup = () => {
		p.pixelDensity(1);
		const testW = 2048;
		const testH = 1024;
		renderer = p.createCanvas(testW, testH);
		calcCanvasRatio(testW, testH);
		calcWindowRatio();
		canvas = renderer.canvas;
		context = canvas.getContext('2d');
		p.frameRate(30);
		drawMode();
		renderer.mousePressed(myMousePressed);
		renderer.mouseReleased(myMouseReleased);
		renderer.mouseMoved(myMouseMoved);
		renderer.touchStarted(myTouchStarted);
		renderer.touchEnded(myTouchEnded);
		renderer.touchMoved(myTouchMoved);
		canvasContainer.addEventListener('wheel', myMouseWheel);
		canvasContainer.addEventListener('mousemove', dragCanvas);
		canvasContainer.addEventListener('mousedown', dragCanvasDown);
		canvasContainer.addEventListener('mouseup', dragCanvasUp);
		canvasContainer.addEventListener('touchmove', dragCanvas);
		canvasContainer.addEventListener('touchstart', dragCanvasDown);
		canvasContainer.addEventListener('touchend', (e) => {
			isPinching = false;
			previousPinchDistance = null;
		});

		// document.querySelector("#select-image").addEventListener('change', changeImage);
		document.querySelector("#select-image").addEventListener('change', (e) => {
			let reader = new FileReader();
			reader.onload = (e) => {
				changeImage(e.target.result);
			}
			reader.readAsDataURL(e.target.files[0]);
		});
		if (getUrlQueries()['img']) {
			changeImage(getUrlQueries()['img']);
		}
		focusCenter();

		window.addEventListener('resize', (e) => {focusCenter()});

	}

	p.draw = () => {
		if (operationMode == OPERATION_MODE.DRAW || operationMode == OPERATION_MODE.ERASER) {
			sketch();
		}
	}

	function calcCanvasRatio(w, h) {
		const g = gcd(w, h);
		ratioW = w / g;
		ratioH = h / g;
		styleWidth = 400;
		styleHeight = ratioH / ratioW * 400;
	}

	function calcWindowRatio() {
		const g = gcd(canvasContainer.clientWidth, canvasContainer.clientHeight);
		windowRatioW = canvasContainer.clientWidth / g;
		windowRatioH = canvasContainer.clientHeight / g;
	}

	function dragCanvasDown(e) {
		if (e.touches) {
			dragCanvasDownPos.x = canvasContainer.scrollLeft + e.touches[0].pageX;
			dragCanvasDownPos.y = canvasContainer.scrollTop + e.touches[0].pageY;
		} else {
			dragCanvasDownPos.x = canvasContainer.scrollLeft + e.pageX;
			dragCanvasDownPos.y = canvasContainer.scrollTop + e.pageY;
		}
	}

	function dragCanvasUp(e) {

	}

	function dragCanvas(e) {
		calcPinch(e);
		if (p.mouseIsPressed && operationMode == OPERATION_MODE.DRAG) {
			if (e.touches) {
				canvasContainer.scrollTo(dragCanvasDownPos.x - e.touches[0].pageX, dragCanvasDownPos.y - e.touches[0].pageY);
			} else {
				canvasContainer.scrollTo(dragCanvasDownPos.x - e.pageX, dragCanvasDownPos.y - e.pageY);
			}
		}
	}

	function calcPinch(e) {
		if (e.touches && e.touches.length > 1) {
			isPinching = true;
			dragMode();
			let x1 = e.touches[0].pageX;
			let y1 = e.touches[0].pageY;
			let x2 = e.touches[1].pageX;
			let y2 = e.touches[1].pageY;

			currentPinchDistance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
			if (!previousPinchDistance)  previousPinchDistance = currentPinchDistance;
			diffPinchDistance = previousPinchDistance - currentPinchDistance;
			previousPinchDistance = currentPinchDistance;
			changeScale(diffPinchDistance * 2);
		}
	}

	function myMouseWheel(e) {
		changeScale(e.deltaY);
	}

	function changeScale(delta) {
		scale -= delta / 500 * scale;
		if (scale < MIN_SCALE) scale = MIN_SCALE;
		if (scale > MAX_SCALE) scale = MAX_SCALE;
		canvas.style.width =  styleWidth * scale + 'px';
		canvas.style.height =  styleHeight * scale + 'px';
		viewOriginalImage.style.width =  styleWidth * scale + 'px';
		viewOriginalImage.style.height =  styleHeight * scale + 'px';
	}

	function myTouchStarted(e) {
		isPressed = true;
		saveHistory();
	}

	function myTouchEnded(e) {
		isPressed = false;
	}

	function myTouchMoved(e) {
	}

	function myMousePressed(e) {
		isPressed = true;
		saveHistory();
	}

	function myMouseReleased(e) {
		isPressed = false;
	}

	function myMouseMoved(e) {
	}

	function sketch() {
		if (operationMode == OPERATION_MODE.DRAW) {
			p.strokeWeight(thickness);
		} else {
			p.strokeWeight(eraserThickness);
		}
		p.stroke(pickr.getColor().toHEXA().toString());
		if (isPressed && !isPinching) {
			p.line(p.mouseX, p.mouseY, p.pmouseX, p.pmouseY);
		}
	}

	function changeImage(url) {
		drawMode();
		p.loadImage(url, (img) => {
			p.resizeCanvas(img.width, img.height);
			calcCanvasRatio(img.width, img.height);
			// p.image(img, 0, 0);
			saveHistory();
			focusCenter();
			originalImage = img;
		})
		const img = new Image();
		img.src = url;
		img.onload = () => {
			viewOriginalImage.width = img.width;
			viewOriginalImage.height = img.height;
			viewOriginalImage.getContext('2d').clearRect(0, 0, viewOriginalImage.width, viewOriginalImage.height);
			viewOriginalImage.getContext('2d').drawImage(img, 0, 0);
		}
	}

	function drawMode() {
		operationMode = OPERATION_MODE.DRAW;
		p.noErase();
		// context.globalCompositeOperation = 'source-over';
		drawModeButton.style.backgroundColor = 'rgba(0, 0, 255, 0.2)';
		dragModeButton.style.backgroundColor = '';
		eraserModeButton.style.backgroundColor = '';
		palettePenMenuContainer.style.display = 'flex';
		paletteEraserMenuContainer.style.display = 'none';
	}

	function dragMode() {
		operationMode = OPERATION_MODE.DRAG;
		drawModeButton.style.backgroundColor = '';
		dragModeButton.style.backgroundColor = 'rgba(0, 0, 255, 0.2)';
		eraserModeButton.style.backgroundColor = '';
		palettePenMenuContainer.style.display = 'none';
		paletteEraserMenuContainer.style.display = 'none';
	}

	function eraserMode() {
		operationMode = OPERATION_MODE.ERASER;
		p.erase();
		// context.globalCompositeOperation = 'destination-out';
		drawModeButton.style.backgroundColor = '';
		dragModeButton.style.backgroundColor = '';
		eraserModeButton.style.backgroundColor = 'rgba(0, 0, 255, 0.2)';
		palettePenMenuContainer.style.display = 'none';
		paletteEraserMenuContainer.style.display = 'flex';
	}

	function saveHistory() {
		redoStack = [];
		if (undoStack.length >= STACK_MAX_SIZE) {
			undoStack.pop();
		}
		undoStack.unshift(context.getImageData(0, 0, p.width, p.height));
	}

	function undo(e) {
		if (undoStack.length <= 0) return;
		if (operationMode == OPERATION_MODE.ERASER) {
			drawMode();
		}
		redoStack.unshift(context.getImageData(0, 0, p.width, p.height));
		const imageData = undoStack.shift();
		context.putImageData(imageData, 0, 0);
	}

	function redo(e) {
		if (redoStack.length <= 0) return
		if (operationMode == OPERATION_MODE.ERASER) {
			drawMode();
		}
		undoStack.unshift(context.getImageData(0, 0, p.width, p.height));
		const imageData = redoStack.shift();
		context.putImageData(imageData, 0, 0);
	}

	function focusCenter() {
		// 比率が大きい方に合わせる
		if (ratioW / ratioH > windowRatioW / windowRatioH) {
			scale = canvasContainer.clientWidth / styleWidth;
		} else {
			scale = canvasContainer.clientHeight / styleHeight;
		}
		scale *= 0.7;
		changeScale(0);
		canvasContainer.scrollTo(canvasContainer.scrollWidth / 2 - canvasContainer.clientWidth / 2,
				canvasContainer.scrollHeight / 2 - canvasContainer.clientHeight / 2);
	}

	function createBlob() {
		let type = 'image/png';
		// canvas から DataURL で画像を出力
		let dataurl = canvas.toDataURL(type);
		// DataURL のデータ部分を抜き出し、Base64からバイナリに変換
		let bin = atob(dataurl.split(',')[1]);
		// 空の Uint8Array ビューを作る
		let buffer = new Uint8Array(bin.length);
		// Uint8Array ビューに 1 バイトずつ値を埋める
		for (let i = 0; i < bin.length; i++) {
			buffer[i] = bin.charCodeAt(i);
		}
		// Uint8Array ビューのバッファーを抜き出し、それを元に Blob を作る
		let blob = new Blob([buffer.buffer], {type: type});
		return blob;
	}

	/*
  function send(e) {
	  e.preventDefault();
     const blob = createBlob();
     let fd = new FormData(sendSketch);
     fd.append('image', blob);
     fetch(sendSketch.action, {
    	 method: 'POST', 
    	 body: fd
      })
     .then((response) => {
    	 if (response.redirected) {
    		 window.location.href = response.url;
    	 }
    	 console.log(response)
     });
  }
	 */
}
