# YTTassist - Pre-alpha

Recently, I began writing my own custom subtitles for my YouTube videos. From past experience, I knew YouTube could display custom-formatted, styled, colored, and positioned text through their subtitle system. I subsequently found it way too difficult to make use of these fancy features easily.

My video editor has caption-editing features, which I found quite handy for precisely syncing my subtitles with the edits in my video. Unfortunately, none of the several different caption formats supported my video editor had complete feature-parity with what I knew YouTube could do. Worse, none of the caption *export* formats supported by my video editor worked perfectly with YouTube. Each format I tried either resulted in unintended spacing, untintended formatting, or the omission of formatting and coloring.

I decided it's finally time to make my own tool to help solve this problem. I still appreciate the ability to precisely sync my captions in my video editor, but I need an external YouTube-compliant formatting tool. I plan to make **YTTassist** just such a tool, capable of reading in `.srt` files containing only text and timing, providing an easy-to-use GUI and custom syntax for adding formatting and styling, and exporting the result in the YouTube-proprietary `.ytt` format (also referred to as `srv3` by YouTube).

At this time, no development has yet been done. I am currently performing tests/experiments to test how compliant with standard `srv3` contents I need to be to still achieve the desired results.