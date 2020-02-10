jQuery(document).ready(function ($) {

    'use strict';


    $(".Modern-Slider").slick({
        autoplay: true,
        speed: 1000,
        slidesToShow: 1,
        slidesToScroll: 1,
        pauseOnHover: false,
        dots: true,
        fade: true,
        pauseOnDotsHover: true,
        cssEase: 'linear',
        // fade:true,
        draggable: false,
        prevArrow: '<button id="prevarrow" class="PrevArrow"></button>',
        nextArrow: '<button id="nextarrow" class="NextArrow"></button>'
    });

    $('#nav-toggle').on('click', function (event) {
        event.preventDefault();
        $('#main-nav').toggleClass("open");
    });

    $('.tab-content > div').hide();
    $('.tab-content > div:first-of-type').show();
    $('.nav-tabs a').click(function (e) {
        e.preventDefault();
        let $this = $(this),
            others = $this.closest('li').siblings().children('a'),
            target = $this.attr('href');
        others.removeClass('active');
        $this.addClass('active');
        $('.tab-content').children('div').hide();
        $(target).show();
    });

    $(".box-video").click(function () {
        $('iframe', this)[0].src += "&amp;autoplay=1";
        $(this).addClass('open');
    });

    $('.owl-carousel').owlCarousel({
        loop: true,
        margin: 30,
        responsiveClass: true,
        responsive: {
            0: {
                items: 1,
                nav: true
            },
            600: {
                items: 2,
                nav: false
            },
            1000: {
                items: 3,
                nav: true,
                loop: false
            }
        }
    });

    var contentSection = $('.content-section, .main-banner');
    var navigation = $('nav');

    //when a nav link is clicked, smooth scroll to the section
    navigation.on('click', 'a', function (event) {
        event.preventDefault(); //prevents previous event
        smoothScroll($(this.hash));
    });

    //update navigation on scroll...
    $(window).on('scroll', function () {
        updateNavigation();
    });
    //...and when the page starts
    updateNavigation();

    /////FUNCTIONS
    function updateNavigation() {
        contentSection.each(function () {
            let sectionName = $(this).attr('id');
            let navigationMatch = $('nav a[href="#' + sectionName + '"]');
            if (($(this).offset().top - $(window).height() / 2 < $(window).scrollTop()) &&
                ($(this).offset().top + $(this).height() - $(window).height() / 2 > $(window).scrollTop())) {
                navigationMatch.addClass('active-section');
            } else {
                navigationMatch.removeClass('active-section');
            }
        });
    }

    function smoothScroll(target) {
        $('body,html').animate({
            scrollTop: target.offset().top
        }, 800);
    }


    $('.button a[href*="#"]').on('click', function (e) {
        e.preventDefault();
        $('html, body').animate({scrollTop: $($(this).attr('href')).offset().top - 0}, 500, 'linear');
    });


    $(function () {
        $('.question-title').click(function (j) {

            var dropDown = $(this).closest('.question-card').find('.question-panel');
            $(this).closest('.acc').find('.question-panel').not(dropDown).slideUp();

            if ($(this).hasClass('active')) {
                $(this).removeClass('active');
            } else {
                $(this).closest('.acc').find('.question-title.active').removeClass('active');
                $(this).addClass('active');
            }

            dropDown.stop(false, true).slideToggle();
            j.preventDefault();
        });
    });

    $("#goto1").click(function () {
        $("#to1").click();
    });

    $("#goto2").click(function () {
        $("#to2").click();
    });

    const target = $(".img-responsive").offset().top + 700;
    let beyond = $("#advice").offset().top - 800; // Beyond point is dynamic due to different tabs
    $(window).scroll(function () {
        if ($(window).scrollTop() >= target && $(window).scrollTop() <= beyond) {
            $('#goback-to-witness').fadeIn(200);    // Fade in the arrow
            beyond = $("#advice").offset().top - 800;
        } else {
            $('#goback-to-witness').fadeOut(200);   // Else fade out the arrow
        }
    });
    $('#goback-to-witness').click(function () {
        $('body,html').animate({
            scrollTop: $("#witness").offset().top    // Scroll to top section
        }, 500);
    });

    $(document).ready(function () {
        $('.popup-gallery').magnificPopup({
            delegate: 'a',
            type: 'image',
            tLoading: "",
            mainClass: 'mfp-with-zoom',
            gallery: {
                enabled: true,
                navigateByImgClick: true,
                preload: [1, 2] // Will preload 1 - before current, and 2 after the current image
            },
            zoom: {
                enabled: true, // By default it's false, so don't forget to enable it
                duration: 300, // duration of the effect, in milliseconds
                easing: 'ease-in-out', // CSS transition easing function
            }
        });
    });

    $(document).ready(function () {
        $('.video-gallery').magnificPopup({
            delegate: 'a',
            type: 'iframe',
            gallery: {
                enabled: true
            }
        });
    });

});