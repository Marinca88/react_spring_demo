package ro.utcn.ps.mi.assignment1.dto;

import lombok.Data;
import ro.utcn.ps.mi.assignment1.entity.Tag;

@Data
public class TagDto {
    private  String text;

    public static TagDto tagDtoFromTag(Tag tag){
        TagDto tagDto = new TagDto();
        tagDto.setText(tag.getText());
        return tagDto;
    }
}
