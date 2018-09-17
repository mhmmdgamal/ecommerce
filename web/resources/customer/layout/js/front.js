$(function () {

    'use strict';

    // Switch Between Login & Signup

    $('.login-page h1 span').click(function () {

        $(this).addClass('selected').siblings().removeClass('selected');

        $('.login-page form').hide();

        $('.' + $(this).data('class')).fadeIn(100);

    });

    // Trigger The Selectboxit

    $("select").selectBoxIt({

        autoWidth: false

    });

    // Hide Placeholder On Form Focus

    $('[placeholder]').focus(function () {

        $(this).attr('data-text', $(this).attr('placeholder'));

        $(this).attr('placeholder', '');

    }).blur(function () {

        $(this).attr('placeholder', $(this).attr('data-text'));

    });

    // Add Asterisk On Required Field

    $('input').each(function () {

        if ($(this).attr('required') === 'required') {

            $(this).after('<span class="asterisk">*</span>');

        }

    });

    // Confirmation Message On Button

    $('.confirm').click(function () {

        return confirm('Are You Sure?');

    });

    $('.live').keyup(function () {

        $($(this).data('class')).text($(this).val());

    });


    // Nice Scroll
    $('html').niceScroll({
        cursorwidth: '14px',
        cursorcolor: '#333',
        cursorborder: '1px solid #333',
        cursorborderradius: 0,
        zindex: 99999
    });

    // Smooth Scroll To Element
    $('.smoothscroll').on('click', function (event) {
        event.preventDefault();
        $('html, body').animate({
            scrollTop: $($(this).data('scroll')).offset().top
        }, 1000);
    });

    // Scroll To Top
    var scrollToTop = $('.scroll-to-top');
    $(window).on('scroll', function () {
        if ($(window).scrollTop() >= 500) {
            if (!scrollToTop.is(':visible')) {
                scrollToTop.fadeIn(300);
            }
        } else {
            if (scrollToTop.is(':visible')) {
                scrollToTop.fadeOut(300);
            }
        }
    });
    $('.scroll-to-top span').on('click', function () {
        $('html, body').animate({
            scrollTop: 0
        }, 1000);
    });

});

