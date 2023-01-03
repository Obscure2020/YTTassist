# YTTassist - Pre-alpha
## Roadmap
- [x] Two-letter Opcodes
- [x] Location-stamped errors
- [x] Font Color implemented
- [x] Anchor Point implemented
- [x] Added Paragraph Timing
- [x] `.srt` Import working
- [ ] `.ytt` Export working (Remember HTML symbol entities!)
- [ ] Intermediate File Extension decided
- [ ] Intermediate File Export working
- [ ] Intermediate File Import working
- [ ] GUI developed
    - [ ] Scrolling Paragraphs Pane
    - [ ] Editing Pane
    - [ ] Error Pane
    - [ ] Adding / Removing / Reordering paragraphs
    - [ ] Paragraph Preview Pane

From there, Alpha phase begins, including additional useful features added to the GUI and more opcodes for more features supported by the `.ytt` format.

Really difficult but cool future feature possiblity: video preview behind paragraph preview.

## Explanation
In 2022, I first tried writing my own custom subtitles for a YouTube video. From past viewing experience, I knew YouTube could display custom-formatted, styled, colored, and positioned text through their subtitle system. I subsequently found it way too difficult to make use of these fancy features easily.

My video editor has caption-editing features, which I found quite handy for precisely syncing my subtitles with the edits in my video. Unfortunately, none of the several different caption formats supported my video editor had complete feature-parity with what I knew YouTube could do. Worse, none of the caption *export* formats supported by my video editor worked perfectly with YouTube. Each format I tried either resulted in unintended spacing, untintended formatting, or the omission of formatting and coloring.

I decided it's finally time to make my own tool to help solve this problem. I still appreciate the ability to precisely sync my captions in my video editor, but I need an external YouTube-compliant formatting tool. I plan to make **YTTassist** just such a tool, capable of reading in `.srt` files containing only text and timing, providing an easy-to-use GUI and custom syntax for adding formatting and styling, and exporting the result in the YouTube-proprietary `.ytt` format (also referred to as `srv3` by YouTube).