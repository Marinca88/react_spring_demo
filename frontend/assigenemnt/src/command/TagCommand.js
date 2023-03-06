import TagInstance from '../model/Tag'

class AddTagsCommand {
	constructor(tags){
		this.tags = tags;
	}

	execute() {
		TagInstance.addTags(this.tags);
	}
}
export default AddTagsCommand;