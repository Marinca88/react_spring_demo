class Tag {
    constructor() {
        this.state = {
            tags:["java","tags"]
        };
    }

    addTags(tags) {
        this.state.tags = [
            ...tags
        ];
    }

    getTags() {
        return this.state.tags;
    }

    getTagByText(text) {
        return this.state.tags.filter(item => item.includes(text));
    }
}
const TagInstance = new Tag();
export default TagInstance;