import TagInstance from '../model/Tag';
import RestClient from '../rest/RestClient';
import InvokerInstance from '../command/Invoker'
import AddTagCommand from '../command/TagCommand'
//const client = new RestClient("username1", "password");
class TagPresenter {
    addTag(text) {
        return TagInstance.addTag(text);
    }
    async getTags(client) {
        var tags = await client.loadAllTags()
        InvokerInstance.invoke(new AddTagCommand(tags))
        return tags;
    }

    async saveTag(tag,client) {
        var tags = await client.createTag(tag)
        return tags;
    }
    getTagByText(text) {
        return TagInstance.getTagByText(text);
    }

}
const TagPresenterInstance = new TagPresenter();
export default TagPresenterInstance;