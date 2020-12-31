const pickrContainer = document.querySelector('.pickr-container');
const themeContainer = document.querySelector('.theme-container');
const themes = [
  'monolith',
  {
    swatches: [
      '#000000',
      '#E60012',
      '#F39800',
      '#fff100',
      '#009944',
      '#0068b7',
      '#1d2088',
      '#920783'
    ],

    defaultRepresentation: 'HEXA',
    components: {
      preview: true,
      opacity: true,
      hue: true,

      interaction: {
        hex: false,
        rgba: false,
        hsva: false,
        input: true,
        clear: false,
        save: false
      }
    }
  }
];

let pickr = null;
let swatcheHistoris = [];

const [theme, config] = themes
const swatcheHistory = [];


const el = document.createElement('p');
pickrContainer.appendChild(el);

// Delete previous instance
if (pickr) {
  pickr.destroyAndRemove();
}

// Create fresh instance
pickr = new Pickr(Object.assign({
  el, theme,
  default: '#000000'
}, config));

let button;

// Set events
/* eslint-disable no-console */
pickr.on('init', instance => {
  button = document.getElementsByClassName('pickr')[0].getElementsByTagName('button')[0];
  // console.log('Event: "init"', instance);
  config.swatches.forEach(() => {
    pickr.addSwatch("#ffffff");
    swatcheHistory.push("#ffffff");
  });
}).on('hide', instance => {
  // console.log('Event: "hide"', instance);
  const {value} = pickr.getRoot().interaction.result;
  if (!value) {
    pickr.setColor(null);
  }
  button.style.color = value;
  swatcheHistory.unshift(value);
  swatcheHistory.pop();
  for (i = 0; i < config.swatches.length; i++) {
    const hexaColor = swatcheHistory[i];
    pickr.removeSwatch(config.swatches.length);
    pickr.addSwatch(hexaColor);
  }
  // console.log(swatcheHistory);

}).on('show', (color, instance) => {
  // console.log('Event: "show"', color, instance);
}).on('save', (color, instance) => {
  // console.log('Event: "save"', color, instance);
}).on('clear', instance => {
  // console.log('Event: "clear"', instance);
}).on('change', (color, source, instance) => {
  const {value} = pickr.getRoot().interaction.result;
  button.style.color = value;
  // console.log('Event: "change"', color, source, instance);
}).on('changestop', (source, instance) => {
  // console.log('Event: "changestop"', source, instance);
}).on('cancel', instance => {
  // console.log('Event: "cancel"', instance);
}).on('swatchselect', (color, instance) => {
  // console.log('Event: "swatchselect"', color, instance);
});
