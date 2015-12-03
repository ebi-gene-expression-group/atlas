"use strict";

//*------------------------------------------------------------------*

var $ = require('jquery');

//*------------------------------------------------------------------*

function StickyHeaderModule(table, stickyIntersect, stickyColumn, stickyHeadRow, stickyWrap, tableHeader) {
    var $t	             = $(table),
        $stickyIntersect = $(stickyIntersect),
        $stickyColumn    = $(stickyColumn),
        $stickyHeadRow   = $(stickyHeadRow),
        $stickyWrap      = $(stickyWrap),
        $tableHeader     = $(tableHeader);

    var calculateAllowance = createStickyAllowanceCallback($t, $stickyHeadRow);
    var stickyReposition = createStickyRepositionCallback($t, $stickyIntersect, $stickyColumn, $stickyHeadRow, $stickyWrap, $tableHeader, calculateAllowance);
    var setWidthAndHeight = createSetStickyWidthHeight($t, $stickyIntersect, $stickyColumn, $stickyHeadRow, $stickyWrap);
    var setWidthsAndReposition = createSetStickyWidthHeightAndRepositionCallback(setWidthAndHeight, stickyReposition);

    return {
        calculateAllowance: calculateAllowance,
        stickyReposition: stickyReposition,
        setWidthAndHeight: setWidthAndHeight,
        setWidthsAndReposition: setWidthsAndReposition
    };

    function createSetStickyWidthHeight($t, $stickyIntersect, $stickyColumn, $stickyHeadRow, $stickyWrap) {
        return function () {
            $t.find("thead th").each(
                function (i) {
                    $stickyHeadRow.find("th").eq(i).width($(this).width());
                }
            ).end().find("tr").each(
                function (i) {
                    $stickyColumn.find("tr").eq(i).height($(this).height());
                    $stickyIntersect.find("tr").eq(i).height($(this).height());
                }
            );

            // Set width of sticky header table and intersect. WebKit does it wrong...
            if ($.browser.webkit) {
                $stickyHeadRow
                    .width($stickyWrap.width())
                    .find("table")
                    .width($t.outerWidth());
                $stickyIntersect.find("table").width($t.find("thead th").eq(0).outerWidth() + 1);
                $stickyColumn.find("table").width($t.find("thead th").eq(0).outerWidth() + 1);
            } else {
                $stickyHeadRow
                    .width($stickyWrap.width())
                    .find("table")
                    .width($t.width());
                $stickyIntersect.find("table").width($t.find("thead th").eq(0).outerWidth());
                $stickyColumn.find("table").width($t.find("thead th").eq(0).outerWidth());
            }

            // Set width of sticky table col
            $stickyIntersect.find("tr:nth-child(2) th").each(function (i) {
                $(this).width($t.find("tr:nth-child(2) th").eq(i).width());
            });
        }
    }

    function createStickyRepositionCallback($t, $stickyIntersect, $stickyColumn, $stickyHeadRow, $stickyWrap, $tableHeader, stickyAllowanceCallback) {
        return function() {
            var $w = $(window);

            // Set position sticky col
            $stickyHeadRow.add($stickyIntersect).add($stickyColumn).css({
                left: $stickyWrap.offset().left,
                top: $stickyWrap.offset().top
            });

            var allowance = stickyAllowanceCallback();

            $stickyHeadRow.find("table").css({
                left: -$stickyWrap.scrollLeft()
            });
            $stickyColumn.css({
                top: $stickyWrap.offset().top - $w.scrollTop(),
                left: $stickyWrap.offset().left
            });

            // 1. Position sticky header based on viewport scrollTop
            if ($w.scrollTop() + $tableHeader.outerHeight() > $t.offset().top &&
                $w.scrollTop() + $tableHeader.outerHeight() < $t.offset().top + $t.outerHeight() - allowance) {
                // When top of viewport is in the table itself
                $stickyHeadRow.add($stickyIntersect).css({
                    visibility: "visible",
                    top: $tableHeader.outerHeight()
                });
            } else if ($w.scrollTop() + $tableHeader.outerHeight() > $t.offset().top + $t.outerHeight() - allowance) {
                $stickyHeadRow.add($stickyIntersect).css({
                    visibility: "visible",
                    top: $t.offset().top + $t.outerHeight() - allowance - $w.scrollTop()
                });
            } else {
                // When top of viewport is above or below table
                $stickyHeadRow.add($stickyIntersect).css({
                    visibility: "hidden",
                    top: $stickyWrap.offset().top - $w.scrollTop()
                });
            }

            // 2. Now deal with positioning of sticky column
            if($stickyWrap.scrollLeft() > 0) {
                // When left of wrapping parent is out of view
                $stickyColumn.css({
                    visibility: "visible",
                    "z-index": 40
                });
            } else {
                $stickyColumn.css({
                    visibility: "hidden",
                    "z-index": -5
                });
            }
        }
    }

    function createSetStickyWidthHeightAndRepositionCallback(setWidthAndHeightCallback, stickyRepositionCallback) {
        return function () {
            setWidthAndHeightCallback();
            stickyRepositionCallback();
        }
    }

    function createStickyAllowanceCallback($t, $stickyHeadRow) {
        return function() {
            var rowHeight = 0;
            // Calculate allowance
            $t.find("tbody tr:lt(1)").each(function () {
                rowHeight += $(this).height();
            });
            return rowHeight + $stickyHeadRow.height();
        }
    }
}

//*------------------------------------------------------------------*

module.exports = StickyHeaderModule;

