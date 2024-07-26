const emblaNode = document.querySelector('.embla');
const options = { loop: true }
const autoPlayPlugin = EmblaCarouselAutoplay();
autoPlayPlugin.delay = 1000;
autoPlayPlugin.play = true;
const plugins = [autoPlayPlugin];
const emblaApi = EmblaCarousel(emblaNode, options, plugins);

// const emblaNode2 = document.querySelector('body > .embla');
// const OPTIONS = { loop: false }
// const emblaApi2 = EmblaCarousel(emblaNode2, OPTIONS, null)
// // const options2 = { loop: false }
// // const plugins2 = [autoPlayPlugin];
// // const emblaApi2 = EmblaCarousel(emblaNode2);
